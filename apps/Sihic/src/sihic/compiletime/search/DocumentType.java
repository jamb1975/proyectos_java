package sihic.compiletime.search;

/**
 * Types of document stored in the search index
 */
public enum DocumentType {
    /**
     * Ensemble Sample, fields: documentType : Type of document, one of this
     * enum's values name : name of the sample description : javadoc description
     * (Not Stored) ensemblePath : ensemble url
     */
    SAMPLE("Samples"),
    /**
     * Java Class, fields: documentType : Type of document, one of this enum's
     * values name : non fully qualified class name description : javadoc
     * description (Not Stored) url : fully qualified oracle.com url package :
     * qualified package
     */
    CLASS("Classes"),
    /**
     * Single JavaFX Property of a Java Class, fields: documentType : Type of
     * document, one of this enum's values name : property name of style
     * "translateX" description : textual description (Not Stored) url : fully
     * qualified oracle.com url className : non fully qualified package :
     * qualified package
     */
    PROPERTY("Properties"),
    /**
     * Single method of a Java Class, fields: documentType : Type of document,
     * one of this enum's values name : the name of the method, not including
     * ()s description : textual description (Not Stored) url : fully qualified
     * oracle.com url className : non fully qualified package : qualified
     * package
     */
    METHOD("Methods"),
    /**
     * Single field of a Java Class, fields: documentType : Type of document,
     * one of this enum's values name : the name of the field description :
     * textual description (Not Stored) url : fully qualified oracle.com url
     * className : non fully qualified package : qualified package
     */
    FIELD("Fields"),
    /**
     * Single enum value of a Java Class, fields: documentType : Type of
     * document, one of this enum's values name : name of the enum value, eg.
     * "FIELD" description : textual description (Not Stored) url : fully
     * qualified oracle.com url className : non fully qualified package :
     * qualified package
     */
    ENUM("Enums"),
    /**
     * Single enum value of a Java Class, fields: documentType : Type of
     * document, one of this enum's values bookTitle : document title chapter :
     * document chapter sectionName : document section sectionUrl : fully
     * qualified oracle.com url
     */
    DOC("Documentation");

    private final String pluralDisplayName;

    DocumentType(String pluralDisplayName) {
        this.pluralDisplayName = pluralDisplayName;
    }

    public String getPluralDisplayName() {
        return pluralDisplayName;
    }
}
