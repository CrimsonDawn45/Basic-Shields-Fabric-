package com.github.crimsondawn45.basicshields.util;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModRecipe {

    private ModItem item;
    private JsonObject recipe;

    public ModRecipe(ModItem item, JsonObject recipe) {
        this.item = item;
        this.recipe = recipe;
    }

    public ModItem getModItem() {
        return this.item;
    }

    public JsonObject getRecipe() {
        return this.recipe;
    }

    /**
     * * generates a JsonObject for a new shield recipe
     * 
     * @param mainItemId    Main Identifier ex.  minecraft:iron_ingot
     * @param isMainTag     Is main a tag?
     * @param plankItemId   Plank Identifier ex. minecraft:planks
     * @param isPlankTag    Are Planks a tag?
     * @param output        Output item Id.
     * 
     * @return Shield recipe in JsonObject form.
     */
    public static JsonObject createShieldRecipe(Identifier mainItemId, boolean isMainTag, Identifier output) {
		String mainType;

		if(isMainTag) {
			mainType = "tag";
		} else {
			mainType = "item";
		}

		return createShapedRecipe(
			//Keys
			Lists.newArrayList('#','i'),
			//Items
			Lists.newArrayList(new Identifier("minecraft","planks"), mainItemId), 
			//Types
			Lists.newArrayList("tag", mainType),
			//Pattern
			Lists.newArrayList(
				"#i#",
				"###",
				" # "
			),
			//Output
			output, 1);
	}

    /**
     * * generates a JsonObject for a new shield recipe
     * 
     * @param tags      List of main identifiers ex. ["minecraft:iron_ingot", "minecraft:logs"]
     * @param output    Output item id.
     * 
     * @return a shield recipe in JsonObject form
     */
    public static JsonObject createShieldRecipe(ArrayList<TagKey<Item>> tags, Identifier output) {
        //Creating a new json object, where we will store our recipe.
        JsonObject json = new JsonObject();
        //The "type" of the recipe we are creating. In this case, a shaped recipe.
        json.addProperty("type", "minecraft:crafting_shaped");
        //This creates:
        //"type": "minecraft:crafting_shaped"

        //Create json element for pattern
        JsonArray pattern = new JsonArray();
        pattern.add("#i#");
        pattern.add("###");
        pattern.add(" # ");
        json.add("pattern", pattern);

        //Create JsonObject for keys
        JsonObject keys = new JsonObject();
        JsonObject plankKey = new JsonObject();
        JsonArray ingotKeyList = new JsonArray();

        //Create # key
        plankKey.addProperty("tag", "minecraft:planks");
        keys.add("#", plankKey);

        //Create i key
        for(TagKey<Item> tag : tags) {
            JsonObject entry = new JsonObject();
            entry.addProperty("tag", tag.toString());
            ingotKeyList.add(entry);
        }
        keys.add("i", ingotKeyList);
        json.add("key", keys);

        //Create JsonObject for result
        JsonObject result = new JsonObject();
        result.addProperty("item", output.toString());
        result.addProperty("count", 1);
        json.add("result", result);
        
		return json;
	}

    /**
     * *generates a JsonObject for any ShapedRecipe with indexes of the arrays being related.
     * 
     * (example)
     * keys = "%, A".
     * Items = "stick, dirt"
     * 
     * Result "% = stick, A = dirt"
     * 
     * @param keys  Array of character keys for pattern
     * @param items Array of items that the keys equal
     * @param type Array of types. If each key is a item OR tag
     * @param pattern Pattern made up of keys, either 2x2 or 3x3.
     * @param output Output item Identifier.
     * @param count How many?
     * 
     * @return Shaped recipe in JsonObject form.
     */
	public static JsonObject createShapedRecipe(ArrayList<Character> keys, ArrayList<Identifier> items, ArrayList<String> type, ArrayList<String> pattern, Identifier output, int count) {
        //Creating a new json object, where we will store our recipe.
        JsonObject json = new JsonObject();
        //The "type" of the recipe we are creating. In this case, a shaped recipe.
        json.addProperty("type", "minecraft:crafting_shaped");
        //This creates:
        //"type": "minecraft:crafting_shaped"
 
        //We create a new Json Element, and add our crafting pattern to it.
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(pattern.get(0));
        jsonArray.add(pattern.get(1));
        jsonArray.add(pattern.get(2));
        //Then we add the pattern to our json object.
        json.add("pattern", jsonArray);
        //This creates:
        //"pattern": [
        //  "###",
        //  " | ",
        //  " | "
        //]
 
        //Next we need to define what the keys in the pattern are. For this we need different JsonObjects per key definition, and one main JsonObject that will contain all of the defined keys.
        JsonObject individualKey; //Individual key
        JsonObject keyList = new JsonObject(); //The main key object, containing all the keys
 
        for (int i = 0; i < keys.size(); ++i) {
            individualKey = new JsonObject();
            individualKey.addProperty(type.get(i), items.get(i).toString()); //This will create a key in the form "type": "input", where type is either "item" or "tag", and input is our input item.
            keyList.add(keys.get(i) + "", individualKey); //Then we add this key to the main key object.
            //This will add:
            //"#": { "tag": "c:copper_ingots" }
            //and after that
            //"|": { "item": "minecraft:sticks" }
            //and so on.
        }
 
        json.add("key", keyList);
        //And so we get:
        //"key": {
        //  "#": {
        //    "tag": "c:copper_ingots"
        //  },
        //  "|": {
        //    "item": "minecraft:stick"
        //  }
        //},
 
        //Finally, we define our result object
        JsonObject result = new JsonObject();
        result.addProperty("item", output.toString());
        result.addProperty("count", count);
        json.add("result", result);
        //This creates:
        //"result": {
        //  "item": "modid:copper_pickaxe",
        //  "count": 1
        //}
 
        return json;
    }

    /**
     * * generates a JsonObject for a new smithing recipe
     * 
     * @param inputId Identifier of the input
     * @param isInputTag Is input a tag?
     * @param additionId Identifier of the addition
     * @param isAdditionTag Is addition a tag?
     * @param outputId Output item Identifier
     * 
     * @return Smithing Recipe in JsonObject Form
     */
    public static JsonObject createSmithingRecipe(Identifier templateId, boolean isTemplateTag, Identifier inputId, boolean isInputTag, Identifier additionId, boolean isAdditionTag, Identifier outputId) {
        //Creating a new json object, where we will store our recipe.
        JsonObject json = new JsonObject();

        //The "type" of the recipe we are creating. In this case, a smithing recipe.
        json.addProperty("type", "minecraft:smithing_transform");
        //This creates
        //"type":"minecraft:smithing"

        /**
         * * Create "template" object contains original item
         */
        JsonObject template = new JsonObject();

        if(isTemplateTag) {
            template.addProperty("tag", templateId.toString());
        } else {
            template.addProperty("item", templateId.toString());
        }
            //Add "base" object
        json.add("template", template);
        //This creates
        //"base":{
        //      "item": "minecraft:stick"
        //}

        /**
         * * Create "base" object contains original item
         */
        JsonObject base = new JsonObject();

        if(isInputTag) {
            base.addProperty("tag", inputId.toString());
        } else {
            base.addProperty("item", inputId.toString());
        }
            //Add "base" object
        json.add("base", base);
        //This creates
        //"base":{
        //      "item": "minecraft:stick"
        //}

        /**
         * *Create "addition" object contains additive
         */
        JsonObject addition = new JsonObject();

        if(isAdditionTag) {
            addition.addProperty("tag", additionId.toString());
        } else {
            addition.addProperty("item", additionId.toString());
        }
            //Add "addition" object
        json.add("addition", addition);
        //This creates
        //"base":{
        //      "item": "minecraft:stick"
        //}


        /**
         * *Create "result" object contains result
         */
        JsonObject result = new JsonObject();
        result.addProperty("item", outputId.toString());

            //Add "result" object
        json.add("result", result);

        return json;
    }
}