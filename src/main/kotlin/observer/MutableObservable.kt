package observer

class MutableObservable<T>(initialValue: T): Observable<T> {

    override var currentValue: T = initialValue
        set(value) {
            field = value
            notifyObservers()
        }

    private val _observer = mutableListOf<Observer<T>>()
    override val observers: List<Observer<T>>
        get() = _observer.toList()

    override fun registerObserver(observer: Observer<T>) {
        _observer.add(observer)
        observer.onChanged(currentValue)
    }

    override fun unregisterObserver(observer: Observer<T>) {
        _observer.remove(observer)
    }
}