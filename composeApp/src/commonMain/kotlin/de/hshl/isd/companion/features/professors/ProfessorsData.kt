package de.hshl.isd.companion.features.professors

fun getProfessors(): List<ProfessorModel> {
    return profs()
}

private fun profs(): List<ProfessorModel> {
    return listOf(
        ProfessorModel(
            1,
            "Prof. Dr.",
            "Holger",
            "Glasmachers",
            "holger.glasmachers@hshl.de",
            "+49 (0)2381 8789-429"
        ),
        ProfessorModel(
            2,
            "Prof. Dr.",
            "Julia",
            "Grewe",
            "julia.grewe@hshl.de",
            "+49 (0)2381 8789-418"
        ),
        ProfessorModel(
            3,
            "M. Sc.",
            "Daniel",
            "Kientopf",
            "daniel.kientopf@hshl.de",
            "+49 (0)2381 8789-451"
        ),
        ProfessorModel(
            4,
            "Prof. Dr.",
            "Thomas",
            "Kirner",
            "thomas.kirner@hshl.de",
            "+49 (0)2381 8789-407"
        ),
        ProfessorModel(
            5,
            "Prof. Dr.",
            "Robin",
            "Nunkesser",
            "robin.nunkesser@hshl.de",
            "+49 (0)2381 8789-442"
        ),
        ProfessorModel(
            6,
            "Prof. Dr.-Ing.",
            "Jan",
            "Pelzl",
            "jan.pelzl@hshl.de",
            "+49 (0)2381 8789-476"
        ),
        ProfessorModel(
            7,
            "Prof. Dr.",
            "Eva",
            "Ponick",
            "eva.ponick@hshl.de",
            "+49 (0)2381 8789-439"
        ),
        ProfessorModel(
            8,
            "Prof. Dr.",
            "Zoia",
            "Runovska",
            "zoia.runovska@hshl.de",
            "+49 (0)2381 8789-426"
        ),
        ProfessorModel(
            9,
            "",
            "Joachim",
            "Strack",
            "joachim.strack@hshl.de",
            "+49 (0)2381 8789-493"
        ),
        ProfessorModel(
            10,
            "Prof. Dr.-Ing.",
            "Alexander",
            "Stuckenholz",
            "alexander.stuckenholz@hshl.de",
            "+49 (0)2381 8789-431"
        ),
        ProfessorModel(
            11,
            "Prof. Dr.",
            "Jens",
            "Thorn",
            "jens.thorn@hshl.de",
            "+49 (0)2381 8789-437"
        ),
        ProfessorModel(
            12,
            "Prof. Dr.",
            "Lara",
            "Tickenbrock",
            "lara.tickenbrock@hshl.de",
            "+49 (0)2381 8789-405"
        ),
        ProfessorModel(
            13,
            "Prof. Dr.-Ing.",
            "Alf",
            "Zips",
            "alf.zips@hshl.de",
            "+49 (0) 2381 8789-449"
        )
    )
}