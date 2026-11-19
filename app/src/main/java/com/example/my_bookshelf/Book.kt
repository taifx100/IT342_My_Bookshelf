package com.example.my_bookshelf

data class Book(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val year: Int = 0,
    val category: String = "",
    val description: String = "",
    val coverColor: String = "sand",
    val coverUrl: String = "",
    val createdAt: Long = 0L
)

val initialBooks = listOf(
    Book(
        id = "book_1",
        title = "شجرتي شجرة البرتقال الرائعة",
        author = "خوسيه ماورو دي فاسكونسيلوس",
        year = 1968,
        category = "أدب إنساني، طفولة",
        description = "رواية إنسانية دافئة تحكي قصة الطفل زيزا، الذي يجد في شجرة برتقال صغيرة صديقًا يشاركه أحلامه وأسراره، في حكاية جميلة عن الطفولة والخيال والصداقة واكتشاف الحياة.",
        coverColor = "sand"
    ),
    Book(
        id = "book_2",
        title = "هاري بوتر وحجر الفيلسوف",
        author = "ج. ك. رولينغ",
        year = 1997,
        category = "خيال، مغامرات",
        description = "رواية خيالية للفتيان واليافعين، تحكي قصة فتى ينتقل إلى مدرسة هوغوورتس، حيث تبدأ مغامراته في عالم مدهش مليء بالصداقة والأسرار والاكتشافات.",
        coverColor = "navy"
    ),
    Book(
        id = "book_3",
        title = "آن في المرتفعات الخضراء",
        author = "لوسي مود مونتغمري",
        year = 1908,
        category = "أدب كلاسيكي، طفولة",
        description = "رواية دافئة تحكي قصة فتاة يتيمة واسعة الخيال تنتقل للعيش في منزل ريفي تحيط به الطبيعة، فتبدأ رحلة مليئة بالمغامرات الصغيرة والأحلام والصداقات الجميلة، وتضفي بروحها المرحة لمسة من البهجة على حياة من حولها.",
        coverColor = "sage"
    )
)
