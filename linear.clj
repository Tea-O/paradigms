;(ns liner)
(defn calc [func]
  (fn [& argv]
    (apply mapv func argv)))

(def v+ (calc +))
(def v- (calc -))
(def v* (calc *))
(def vd (calc /))
(def m+ (calc v+))
(def m- (calc v-))
(def m* (calc v*))
(def md (calc vd))
(def c+ (calc m+))
(def c- (calc m-))
(def c* (calc m*))
(def cd (calc md))

(defn determiner [vec1 vec2 ind1 ind2]
  (- (* (nth vec1 ind1) (nth vec2 ind2)) (* (nth vec2 ind1) (nth vec1 ind2))))

(defn vect [vec1 vec2]
  (vector (determiner vec1 vec2 1 2) (determiner vec1 vec2 2 0) (determiner vec1 vec2 0 1)))

(defn scalar [& argv]
  (apply + (apply v* argv)))

(defn v*s [vec, scal]
  (mapv (fn [elem] (* elem scal)) vec))

(defn m*s [matrix, scal]
  (mapv (fn [vec] (v*s vec scal)) matrix))

(defn m*v [matrix vec]
  (mapv (partial scalar vec) matrix))

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn m*m [matrix1 matrix2]
  (mapv (partial m*v (transpose matrix2)) matrix1))