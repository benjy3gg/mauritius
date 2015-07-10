package com.support.android.mauritius;

/**
 * Created by benjy3gg on 06.07.2015.
 */
public class Drink {
    protected String name;
    protected String category;
    protected Number price;
    protected Number rating;
    protected Number numRatings;
    protected String imgUrl;
    protected Number[] hearts;
    protected String[] likedBy;
    protected Ingredient[] ingredients;

    public Drink(String name, String category, Number price, String imgUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imgUrl = imgUrl;
        this.rating = 0;
        this.numRatings = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public Number getRating() {
        return rating;
    }

    public void setRating(Number rating) {
        this.rating = rating;
    }

    public Number getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(Number numRatings) {
        this.numRatings = numRatings;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Number[] getHearts() {
        return hearts;
    }

    public void setHearts(Number[] hearts) {
        this.hearts = hearts;
    }

    public String[] getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(String[] likedBy) {
        this.likedBy = likedBy;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }
}
