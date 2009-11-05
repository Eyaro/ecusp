(comment
(ns ecore.utils.view)
(defn mkcoll [item]
 (cond
   (nil? item) nil
   (coll? item) item
   :else (seq (list item))))
 
(defn assert-method-names [func meths  & members] 
  (assert 
    (every? (fn [item]
                (some #{(func item)} members))
        meths)))
        
(defmacro new-view
  [classname-sym & body]
  (let [classname        (name classname-sym)
        last-dot         (.lastIndexOf classname ".")
        simple-classname (if (not= last-dot -1)
                           (.substring classname (inc last-dot))
                           classname)
        prefix (str (gensym simple-classname))
        prefixed         #(symbol (str prefix %))
        hinted-this      (with-meta 'this {:tag classname-sym})
        meths (map (fn [method] 
                         `(defn ~(prefixed (name (first method)))
                             [~hinted-this ~@(mkcoll (second method))]
                            ~@(nnext method)))
                        body)]
     (assert-method-names 
        #(name (first %)) body
        "createPartControl" "setFocus")
    `(do 
       (gen-class
         :name    ~classname-sym
         :extends org.eclipse.ui.part.ViewPart
         :prefix  ~prefix
         :init    ~'init
         :main    false)
       (defn ~(prefixed "init")
         []
         [[] []])
		~@meths))) 
 

(new-view rcp.MyView
  (:createPartControl parent
   ; (.setText (Text. parent SWT/BORDER) "Imagine~!")
    (println "HI")
    )
  (:setFocus nil ))
  
  
  )