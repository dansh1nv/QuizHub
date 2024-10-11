package ru.dansh1nv.quiz_list_domain.models

enum class PaymentMethod(
    val id: Int,
    val title: String,
) {
    CASH(
        id = 0,
        title = "Наличные с человека"
    ),
    CASH_AND_CARD(
        id = 2,
        title = "Наличные или карта"
    ),
    ONLINE(
        id = 3,
        title = "С команды оплата онлайн"
    );
}