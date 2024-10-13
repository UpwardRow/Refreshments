package com.adowney.refreshments

enum class PredefinedQuickFilters(val quickFilterName: String, val apiLabel: String) {
    ALCOHOLFREE("Alcohol Free", "alcohol-free"),
    CELERYFREE("Celery-Free", "celery-free"),
    CRUSTACEANFREE("Crustcean-Free", "crustacean-free"),
    DAIRYFREE("Dairy-Free", "dairy-free"),
    DASH("DASH", "DASH"),
    EGGFREE("Egg-Free", "egg-free"),
    FISHFREE("Fish-Free", "fish-free"),
    FODMAPFREE("FODMAP-Free", "fodmap-free"),
    GLUTENFREE("Gluten-Free", "gluten-free"),
    IMMUNOSUPPORTIVE("Immuno-Supportive", "immuno-supportive"),
    KETOFRIENDLY("Keto-Friendly", "keto-friendly"),
    KIDNEYFRIENDLY("Kidney-Friendly", "kidney-friendly"),
    KOSHER("Kosher", "kosher"),
    LOWPOTASSIUM("Low Potassium", "low-potassium"),
    LOWSUGAR("Low Sugar", "low-sugar"),
    LUPINEFREE("Lupine-Free", "lupine-free"),
    NOOILADDED("No oil added", "No-oil-added"),
    MEDITERRANEAN("Mediterranean", "mediterranean"),
    MOLLUSKFREE("Mollusk-Free", "mollusk-free"),
    MUSTARDFREE("Mustard-Free", "mustard-free"),
    PALEO("Paleo", "paleo"),
    PEANUTFREE("Peanut-Free", "peanut-free"),
    PESCATARIAN("Pescatarian", "pescatarian"),
    PORKFREE("Pork-Free", "pork-free"),
    REDMEATFREE("Red-Meat-Free", "red-meat-free"),
    SESAMEFREE("Sesame-Free", "sesame-free"),
    SHELLFISHFREE("Shellfish-Free", "shellfish-free"),
    SOYFREE("Soy-Free", "soy-free"),
    SUGARCONSCIOUS("Sugar-Conscious", "sugar-conscious"),
    SULFITEFREE("Sulfite-Free", "sulfite-free"),
    TREENUTFREE("Tree-Nut-Free", "tree-nut-free"),
    VEGAN("Vegan", "vegan"),
    VEGETARIAN("Vegetarian", "vegetarian"),
    WHEATFREE("Wheat-Free", "wheat-free");

    companion object{
        fun getAllQuickFilters(): List<PredefinedQuickFilters>{
            return values().toList()
        }
        fun getApiLabelByName(quickFilterName: String): String? {
            return values().find { it.quickFilterName == quickFilterName }?.apiLabel
        }
    }
}