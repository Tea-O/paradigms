;load-file "parser.clj"

(defn binOperation [f]
  (fn [& args]
    (fn [var] (apply f (mapv (fn [argv] (argv var)) args)))))

(defn qued [x]
  (reduce * (repeat 2 x)))

(defn constant [val]
  (fn [argv] (double val)))

(defn variable [var]
  (fn [argv] double (get argv var)))

(def subtract (binOperation -))
(def negate (binOperation -))
(def add (binOperation +))
(def multiply (binOperation *))
(defn divide [l r] #(/ (double (l %1)) (double (r %1))))
(def exp (binOperation #(Math/exp %1)))
(def ln (binOperation #(Math/log %1)))

(def operation {'+      add
                '-      subtract
                'negate negate
                '*      multiply
                '/      divide
                'exp    exp
                'ln     ln})
(defn parse [st]
  (cond
    (number? st) (constant st)
    (symbol? st) (variable (str st))
    :else (apply (operation (nth st 0)) (mapv parse (rest st)))))

(defn parseFunction [expr]
  (parse (read-string expr)))

(defn proto-get
  ;"Returns object property respecting the prototype chain"
  ([obj key] (proto-get obj key nil))
  ([obj key default]
   (cond
     (contains? obj key) (get obj key)
     (contains? obj :prototype) (recur (obj :prototype) key default)
     :else default)))
(defn proto-call
  ; "Calls object method respecting the prototype chain"
  [this key & args]
  (apply (proto-get this key) this args))
;(defn evaluate [ex arg] (proto-call ))
(defn field [key]
  ;"Creates field"
  (fn
    ([this] (proto-get this key))
    ([this def] (proto-get this key def))))

(defn method [key]
  (fn [obj & args] (apply proto-call obj key args)))

(def evaluate (method :evaluate))
(def diff (method :diff))
(def toString (method :toString))
(def function (field :function))
(def dif (method :dif))
(def sign (field :sign))
(def oper (field :oper))
(defn Constant [x]
  {
   :evaluate (fn [this args] (double x))
   :toString (fn [this] (str x))
   :diff     (fn [this args] (Constant 0))
   })

(defn Variable [x]
  {
   :evaluate (fn [this args] (get args x))
   :toString (fn [this] (str x))
   :diff     (fn [this args] (cond
                               (= x args) (Constant 1)
                               :else (Constant 0)))
   })
(def BinOperation
  {
   :evaluate (fn [this args] (apply (function this) (mapv (fn [x] (evaluate x args)) (oper this))))
   :toString (fn [this] (str "(" (sign this) " " (clojure.string/join " " (mapv toString (oper this)))")"))
   :diff (fn [this var] (dif this var))})

(def difur (fn [this args] (map (fn [x] (diff x args)) (oper this))))

(defn creat [sign func dif]
  (let [pr {:prototype BinOperation
            :sign sign
            :function func
            :dif dif
            }]
  (fn [& oper]
  {:prototype pr
   :oper (vec oper)})))


(def Add (creat '+ + (fn [this args] (apply Add (difur this args)))))

(def Subtract (creat '- - (fn [this args] (apply Subtract (difur this args)))))

(def Negate (creat 'negate - (fn [this args] (apply Negate (difur this args)))))

(defn Multiply [f g]
  {
   :evaluate (fn [this args] (* (evaluate f args) (evaluate g args)))
   :toString (fn [this] (str "(* " (toString f) " " (toString g) ")"))
   :diff     (fn [this args] (Add
                               (Multiply (diff f args) g)
                               (Multiply f (diff g args))))
   })

(defn Divide [f g]
  {
   :evaluate (fn [this args] (/ (double (evaluate f args)) (double (evaluate g args))))
   :toString (fn [this] (str "(/ " (toString f) " " (toString g) ")"))
   :diff     (fn [this args] (Divide (Subtract
                                       (Multiply (diff f args) g)
                                       (Multiply f (diff g args)))
                                     (Multiply g g)))
   })
; Можно было вынести больше
(defn Ln [f]
  {
   :evaluate (fn [this args] (Math/log (evaluate f args)))
   :toString (fn [this] (str "(ln " (toString f) ")"))
   :diff     (fn [this args] (Multiply (Divide (Constant 1) f) (diff f args)))
   })

(defn Exp [f]
  {
   :evaluate (fn [this args] (Math/exp (evaluate f args)))
   :toString (fn [this] (str "(exp " (toString f) ")"))
   :diff     (fn [this args] (Multiply (Exp f) (diff f args)))
   })
;(def Ln (creat 'ln #(Math/log %1) (fn [this args] (diff this args))))

;(def Exp (creat 'exp #(Math/exp %1) (fn [this args] (diff this args))))

(def operationObj {'+      Add
                   '-      Subtract
                   'negate Negate
                   '*      Multiply
                   '/      Divide
                   'exp    Exp
                   'ln     Ln
                   })

(defn parseObj [st]
  (cond
    (number? st) (Constant st)
    (symbol? st) (Variable (str st))
    :else (apply (operationObj (nth st 0)) (mapv parseObj (rest st)))))

(defn parseObject [expr]
  (parseObj (read-string expr)))