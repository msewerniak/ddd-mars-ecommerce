package com.mars.ecommerce.sales

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

@AnalyzeClasses(packages = "com.mars.ecommerce")
class ProjectStructureTest {

    @ArchTest
    public static final ArchRule shipping_should_not_depend_on_sales =
            noClasses().that().resideInAPackage("..shipping..")
                    .should().dependOnClassesThat().resideInAPackage("..sales..")

    @ArchTest
    public static final ArchRule domain_should_not_depend_on_infrastructure =
            noClasses().that().resideInAPackage("..domain..").and().haveNameNotMatching(".*Test")
                    .should().dependOnClassesThat().resideInAPackage("..infrastructure..")

    @ArchTest
    public static final ArchRule domain_should_not_depend_on_api =
            noClasses().that().resideInAPackage("..domain..").and().haveNameNotMatching(".*Test")
                    .should().dependOnClassesThat().resideInAPackage("..api..")
    
}
