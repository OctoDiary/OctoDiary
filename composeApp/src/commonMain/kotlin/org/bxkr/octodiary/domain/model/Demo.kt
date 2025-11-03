package org.bxkr.octodiary.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.bxkr.octodiary.domain.model.employee.Employee
import org.bxkr.octodiary.domain.model.employee.EmployeeContacts
import org.bxkr.octodiary.domain.model.employee.EmployeeType
import org.bxkr.octodiary.domain.model.event.Event
import org.bxkr.octodiary.domain.model.event.EventLocation
import org.bxkr.octodiary.domain.model.event.EventType
import org.bxkr.octodiary.domain.model.group.Group
import org.bxkr.octodiary.domain.model.group.GroupMember
import org.bxkr.octodiary.domain.model.group.GroupType
import org.bxkr.octodiary.domain.model.homework.Homework
import org.bxkr.octodiary.domain.model.homework.HomeworkEntry
import org.bxkr.octodiary.domain.model.mark.Mark
import org.bxkr.octodiary.domain.model.mark.MarkComponent
import org.bxkr.octodiary.domain.model.mark.MarkGroupResult
import org.bxkr.octodiary.domain.model.organization.Organization
import org.bxkr.octodiary.domain.model.organization.OrganizationContacts
import org.bxkr.octodiary.domain.model.organization.OrganizationType
import org.bxkr.octodiary.domain.model.ranking.Ranking
import org.bxkr.octodiary.domain.model.ranking.RankingMember
import org.bxkr.octodiary.domain.model.ranking.RankingType
import org.bxkr.octodiary.domain.model.student.Student
import org.bxkr.octodiary.domain.model.subject.Subject
import org.bxkr.octodiary.domain.model.user.UserProfile
import org.bxkr.octodiary.domain.model.user.UserType
import org.bxkr.octodiary.domain.model.visits.Visit
import org.bxkr.octodiary.domain.model.visits.VisitDay

// Random data. Any matches are unintentional
object Demo {
    val student = Student(
        studentId = "312582",
        firstName = "Иван",
        lastName = "Иванов",
        middleName = "Иванович",
    )

    val user = UserProfile(
        primaryId = "75429341",
        firstName = "Иван",
        lastName = "Иванов",
        middleName = "Иванович",
        userType = UserType.Student,
        students = listOf(student)
    )

    val employee1 = Employee(
        fullName = "Зайцева Марина Ивановна",
        type = EmployeeType.Teacher,
        contacts = EmployeeContacts(
            phoneNumber = "+79235320901",
            email = "mathematician_777@yandex.ru",
            personalClassroom = "419"
        ),
        affiliation = "Учитель математики"
    )

    val employee2 = Employee(
        fullName = "Синицын Григорий Александрович",
        type = EmployeeType.Teacher,
        contacts = EmployeeContacts(
            phoneNumber = "+79040310231",
            email = "gramotey_1965@mail.ru",
            personalClassroom = "324"
        ),
        affiliation = "Учитель русского языка"
    )

    val employee3 = Employee(
        fullName = "Жуков Михаил Дмитриевич",
        type = EmployeeType.Headmaster,
        contacts = EmployeeContacts(
            phoneNumber = "+74950231475",
            email = "zhukovmd@edu.mos.ru"
        )
    )

    val organization = Organization(
        fullName = "Государственное бюджетное общеобразовательное учреждение города Москвы \"Школа № 8312\"",
        shortName = "ГБОУ Школа № 8312",
        type = OrganizationType.School,
        personnel = listOf(employee1, employee2, employee3),
        contacts = OrganizationContacts(
            phoneNumber = "+74952651849",
            email = "8312@edu.mos.ru",
            website = "8312.mskobr.ru",
            physicalAddress = "город Москва, проспект Свободы, дом 20, строение 30"
        )
    )

    val mainGroup = Group(
        groupId = "5267389",
        fullName = "Класс 10 А",
        shortName = "10А",
        type = GroupType.Class,
        level = 10,
        members = listOf(
            GroupMember(
                studentId = "312582",
                name = "Иванов Иван Иванович"
            ),
            GroupMember(
                studentId = "7182301",
                name = "Кроликов Станислав Артемович"
            ),
            GroupMember(
                studentId = "7182302",
                name = "Конников Михаил Геннадьевич"
            ),
            GroupMember(
                studentId = "7182303",
                name = "Кожевникова Александра Денисовна"
            ),
            GroupMember(
                studentId = "7182304",
                name = "Ласточкина Ева Алексеевна"
            ),
            GroupMember(
                studentId = "7182305",
                name = "Чудова Дарья Владимировна"
            ),
        )
    )

    val additionalGroup = Group(
        groupId = "5267389",
        fullName = "Математика 10 А 2",
        shortName = "10А2-Мат",
        type = GroupType.Additional
    )

    val oneDaySchedule = listOf(
        Event(
            eventId = "123",
            eventName = "Разговоры о важном",
            type = EventType.Additional,
            timeStart = LocalDateTime(2025, 10, 20, 9, 0),
            timeEnd = LocalDateTime(2025, 10, 20, 9, 40),
            location = EventLocation("312", "к1")
        ),
        Event(
            eventId = "123",
            eventName = "Русский язык",
            type = EventType.MainPlan,
            timeStart = LocalDateTime(2025, 10, 20, 9, 50),
            timeEnd = LocalDateTime(2025, 10, 20, 10, 30),
            location = EventLocation("203", "к1"),
            homework = Homework("321", "Выучить паронимы"),
            marks = listOf(Mark(Subject("012", ""), listOf(MarkComponent.IntegerMark(5, 2))))
        ),
        Event(
            eventId = "123",
            eventName = "Русский язык",
            type = EventType.MainPlan,
            timeStart = LocalDateTime(2025, 10, 20, 10, 45),
            timeEnd = LocalDateTime(2025, 10, 20, 11, 25),
            location = EventLocation("203", "к1"),
            homework = Homework("321", "Выучить паронимы")
        ),
        Event(
            eventId = "123",
            eventName = "Алгебра",
            type = EventType.MainPlan,
            timeStart = LocalDateTime(2025, 10, 20, 11, 40),
            timeEnd = LocalDateTime(2025, 10, 20, 12, 20),
            location = EventLocation("211", "к1"),
            homework = Homework("321", "Решить задачи из прикрепленного файла"),
            marks = listOf(Mark(Subject("012", ""), listOf(MarkComponent.IntegerMark(3))))
        ),
        Event(
            eventId = "123",
            eventName = "Алгебра",
            type = EventType.MainPlan,
            timeStart = LocalDateTime(2025, 10, 20, 12, 40),
            timeEnd = LocalDateTime(2025, 10, 20, 13, 20),
            location = EventLocation("211", "к1"),
            homework = Homework("321", "Решить задачи из прикрепленного файла")
        ),
        Event(
            eventId = "123",
            eventName = "История",
            type = EventType.MainPlan,
            timeStart = LocalDateTime(2025, 10, 20, 13, 40),
            timeEnd = LocalDateTime(2025, 10, 20, 14, 20),
            location = EventLocation("302", "к1"),
            homework = Homework("321", "Параграф 2, вопросы 3-5")
        ),
        Event(
            eventId = "123",
            eventName = "Английский язык",
            type = EventType.MainPlan,
            timeStart = LocalDateTime(2025, 10, 20, 14, 40),
            timeEnd = LocalDateTime(2025, 10, 20, 15, 20),
            location = EventLocation("113", "к1"),
            homework = Homework("321", "Модуль 3b, упр. 6,7")
        ),
        Event(
            eventId = "123",
            eventName = "Основы безопасности и защиты Родины",
            type = EventType.MainPlan,
            timeStart = LocalDateTime(2025, 10, 20, 15, 30),
            timeEnd = LocalDateTime(2025, 10, 20, 16, 10),
            location = EventLocation("401", "к1"),
            homework = Homework("321", "Выполнить ЦДЗ")
        ),
    )

    val schedule = (0..6).map { i ->
        oneDaySchedule.map { event ->
            event.copy(
                timeStart = event.timeStart!!.run {
                    LocalDateTime(
                        year,
                        month,
                        day + i,
                        hour,
                        minute
                    )
                },
                timeEnd = event.timeEnd!!.run { LocalDateTime(year, month, day + i, hour, minute) }
            )
        }
    }.flatten()

    val subject1 = Subject(
        id = "2134123",
        name = "Русский язык"
    )

    val subject2 = Subject(
        id = "3124123",
        name = "Алгебра"
    )

    val subject3 = Subject(
        id = "4124131",
        name = "Геометрия"
    )

    val subject4 = Subject(
        id = "56234123",
        name = "Физика"
    )

    val subject5 = Subject(
        id = "6423452",
        name = "Информатика"
    )

    val homeworkEntries = listOf(
        HomeworkEntry(
            subject = subject1,
            deadline = LocalDate(2025, 10, 20),
            homeworks = listOf(
                Homework("01231", "Выучить паронимы", isDone = false),
                Homework("01232", "Знать ударения к заданию 4", isDone = false),
            )
        ),
        HomeworkEntry(
            subject = subject2,
            deadline = LocalDate(2025, 10, 21),
            homeworks = listOf(
                Homework("01233", "Выполнить номера 1-10 из задачника", isDone = true)
            )
        ),
        HomeworkEntry(
            subject = subject3,
            deadline = LocalDate(2025, 10, 21),
            homeworks = listOf(
                Homework("01234", "3.123.1, 3.125.1, 3.129.1", isDone = false)
            )
        ),
        HomeworkEntry(
            subject = subject4,
            deadline = LocalDate(2025, 10, 22),
            homeworks = listOf(
                Homework("01235", "Подготовиться к коллоквиуму", isDone = false),
                Homework("01236", "Решить 500 вариантов", isDone = false)
            )
        ),
        HomeworkEntry(
            subject = subject5,
            deadline = LocalDate(2025, 10, 23),
            homeworks = listOf(
                Homework("01237", "Задание на sdo.8312.ru")
            )
        )
    )

    val event = Event(
        eventId = "123",
        eventName = "Английский язык",
        type = EventType.MainPlan,
        timeStart = LocalDateTime(2025, 10, 20, 14, 40),
        timeEnd = LocalDateTime(2025, 10, 20, 15, 20),
        location = EventLocation("113", "к1"),
        homework = Homework("321", "Модуль 3b, упр. 6,7"),
        employee = Employee(
            fullName = "Дронова Дарья Игоревна",
            type = EmployeeType.Teacher
        ),
        marks = listOf(
            Mark(
                subject = Subject("3123", "Английский язык"),
                components = listOf(
                    MarkComponent.IntegerMark(4, 2)
                ),
                workType = "Контрольная работа"
            ),
            Mark(
                subject = Subject("3123", "Английский язык"),
                components = listOf(
                    MarkComponent.IntegerMark(5)
                ),
                workType = "Ответ на уроке"
            ),
        )
    )

    val mark = Mark(
        subject = Subject("3123", "Английский язык"),
        components = listOf(
            MarkComponent.IntegerMark(4, 2)
        ),
        workType = "Контрольная работа",
        setBy = Employee(
            fullName = "Дронова Дарья Игоревна",
            type = EmployeeType.Teacher
        ),
        setAt = LocalDateTime(2025, 10, 20, 19, 30),
        groupResults = listOf(
            MarkGroupResult(
                "5",
                5
            ),
            MarkGroupResult(
                "4",
                10
            ),
            MarkGroupResult(
                "3",
                2
            ),
            MarkGroupResult(
                "2",
                1
            ),
        )
    )

    val rankingMembers = listOf(
        RankingMember(
            place = 1,
            studentId = "312582",
            result = "4.8",
            fullName = "Иванов Иван Иванович"
        ),
        RankingMember(
            place = 2,
            studentId = "7182301",
            result = "4.7",
            fullName = "Кроликов Станислав Артемович"
        ),
        RankingMember(
            place = 3,
            studentId = "7182302",
            result = "4.6",
            fullName = "Конников Михаил Геннадьевич"
        ),
        RankingMember(
            place = 4,
            studentId = "7182303",
            result = "4.5",
            fullName = "Кожевникова Александра Денисовна"
        ),
        RankingMember(
            place = 5,
            studentId = "7182304",
            result = "4.4",
            fullName = "Ласточкина Ева Алексеевна"
        ),
        RankingMember(
            place = 6,
            studentId = "7182305",
            result = "4.3",
            fullName = "Чудова Дарья Владимировна"
        ),
    )

    val ranking = Ranking(
        members = rankingMembers,
        type = RankingType.Common,
        updatedAt = LocalDateTime(2025, 10, 20, 1, 0)
    )

    val visits = (0..13).map { LocalDate(2025, 10, 1 + it) }.map {
        VisitDay(
            date = it,
            visits = listOf(
                Visit(
                    enterTime = "09:" + (30 + kotlin.random.Random.nextInt(0, 10)).toString(),
                    exitTime = "16:" + (kotlin.random.Random.nextInt(0, 10)).toString(),
                )
            )
        )
    }
}