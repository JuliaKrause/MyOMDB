package com.juliakrause.generator;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;

import de.greenrobot.daogenerator.Entity;

import de.greenrobot.daogenerator.Property;

import de.greenrobot.daogenerator.Schema;


public class Generator {
    //../../../../../../../app/src/main/java/
    private static final String TARGET_FOLDER = "app/src/main/java/";

    private static final int SCHEMA_VERSION = 1;

    private void generateSchema() {
        Schema schema = new Schema(SCHEMA_VERSION, "com.juliakrause.greendao.generated");

        // defining the Movie entity

        Entity movie = schema.addEntity("Movie");

        //movie.setTableName("MOVIE"); // note that we can actually manually provide a table name!

        movie.addIdProperty();

        movie.addStringProperty("imdbId").notNull();
        movie.addStringProperty("title").notNull();
        //movie.addStringProperty("title").notNull().index(); // setting an index
        movie.addStringProperty("type").notNull();
        movie.addStringProperty("year").notNull();
        movie.addIntProperty("toWatch").notNull();
        movie.addIntProperty("favorite").notNull();

        try {
            new DaoGenerator().generateAll(schema, TARGET_FOLDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static final void main(String[] args) throws Throwable {
        // we are generating the schema here
        new Generator().generateSchema();
    }


}
