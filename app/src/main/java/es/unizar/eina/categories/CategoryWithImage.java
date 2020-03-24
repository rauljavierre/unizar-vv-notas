package es.unizar.eina.categories;

public class CategoryWithImage {

    private String categoryName;
    private int icon;

    public CategoryWithImage(String categoryName, int icon){
        this.categoryName = categoryName;
        this.icon = icon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getIcon() {
        return icon;
    }
}
