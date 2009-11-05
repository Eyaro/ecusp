(ns gui.coremig
 (:import
   (net.miginfocom.swt MigLayout)
   (org.eclipse.swt.widgets Composite Shell 
    Display Control TabItem )
   (org.eclipse.swt SWT)
   )
   )

(def #^{:private true} *defaults*
  {:Label SWT/NONE 
   :Button SWT/PUSH
   :Text SWT/NONE
   :SashForm SWT/VERTICAL})
(def #^{:private true} *prefix* "swt.widgets.")

(defn sym-as-key [sym]
  (read-string (str ":" (str sym))))

(defn miglayout-widget-default-style [id]
  (let [result (or ((sym-as-key id) *defaults*) SWT/NONE)]
    (if-not (integer? result) (throw (Exception. (str "No default found for widget " id))))
    result))

(defn third [coll] (first (nnext coll)))

(defn -checkSubclass [this])
(defn init [#^Composite composite #^Integer default args]
(println args) (println "GO")
  (condp = (count args)
    3  [[composite (first args)] []]
    [[composite
      (if (integer? (first args))
        (first args)
        default)]
     []])) 

(defn set-layout [this arg]
  (if (string? arg) (.setLayoutData this arg)
    (.setLayout this arg)))
     
(defn -post-init [this & args] (println args) (println "HI")
  (condp = (count args) 
    3 (set-layout this (third args))
    (if-not (integer? (second args))
      (set-layout this (second args)))))

  
(defmacro generate-swt-binding [nm]
  (let [the-name (if (coll? nm) (first nm) nm)
        extend (if (coll? nm) (second nm) (str "org.eclipse.swt.widgets." the-name))
        init (str  (gensym the-name))] 
    `(do
      (gen-class
       :name ~(str *prefix* the-name)
       :init ~(read-string init)
       :post-init ~'post-init
       :constructors {[org.eclipse.swt.widgets.Composite String] [org.eclipse.swt.widgets.Composite ~'int]
                      [org.eclipse.swt.widgets.Composite Integer] [org.eclipse.swt.widgets.Composite ~'int]
                      [org.eclipse.swt.widgets.Composite org.eclipse.swt.widgets.Layout] [org.eclipse.swt.widgets.Composite ~'int]
                      [org.eclipse.swt.widgets.Composite Integer String] [org.eclipse.swt.widgets.Composite ~'int]
                      [org.eclipse.swt.widgets.Composite Integer org.eclipse.swt.widgets.Layout] [org.eclipse.swt.widgets.Composite ~'int]}
       :extends ~extend)
       (defn  ~(read-string (str "-" init)) [composite# & args#]
         (init composite# ~(miglayout-widget-default-style the-name) args#)))))
  
  (defmacro generate-swt-bindings [& nms]
    `(do ~@(map (fn [nm] `(generate-swt-binding ~nm)) nms)))

(generate-swt-bindings  
    "Label" "Button" "Text" "Composite"
    ("SashForm" "org.eclipse.swt.custom.SashForm")
    ("StyledText" "org.eclipse.swt.custom.StyledText")
    )
 ;;;;;;;;;;
 ;;;;;;;;;;;
 ;;;;;;;;;;
 