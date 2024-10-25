package model

abstract class IPublisher {
    private val subscribers = mutableListOf<ISubscriber>()
    fun notifySubscribers() {
        subscribers.forEach() {
            it.update()
        }
    }

    fun subscribe(subscriber: ISubscriber) {
        subscribers.add(subscriber)
        subscriber.update()
    }

    fun unsubscribe(subscriber: ISubscriber) {
        subscribers.remove(subscriber)
    }
}
