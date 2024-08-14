rootProject.name = "hexagonal-spring-skeleton"

include(":shared")
project(":shared").projectDir = file("src/shared")
include(":bounded-context")
project(":bounded-context").projectDir = file("src/bounded-context")