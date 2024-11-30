package ru.dansh1nv.quiz_list_domain.models.common

enum class PaymentMethod(
    val id: Int,
    val rudaId: String,
    val title: String,
) {
    CASH(
        id = 0,
        rudaId = "",
        title = "Наличные с человека"
    ),
    CASH_AND_CARD(
        id = 2,
        rudaId = "Оплата наличными и картой",
        title = "Наличные или карта"
    ),
    ONLINE(
        id = 3,
        rudaId = "",
        title = "С команды оплата онлайн"
    );
}