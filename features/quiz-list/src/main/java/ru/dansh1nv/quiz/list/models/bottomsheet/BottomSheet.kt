package ru.dansh1nv.quiz.list.models.bottomsheet

sealed class BottomSheet {

    data object SortingBottomSheet : BottomSheet()
    data object FiltersBottomSheet : BottomSheet()
    data object LocationBottomSheet : BottomSheet()

}