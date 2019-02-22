
package com.ghaemi;

/**
 *  A template for storing the data retrieved from here:
 *  https://twitter.com/i/profiles/show/{id}/timeline/tweets?include_available_features=1&include_entities=1&max_position={min-position}&reset_error_state=false";
                 
 * 
 * @author Ghaemi
 */
public class TwitterJson {
    private String min_position;
    private String has_more_items;
    private String items_html;
    private String new_latent_count;

    public String getMin_position() {
        return min_position;
    }

    public void setMin_position(String min_position) {
        this.min_position = min_position;
    }

    public String getHas_more_items() {
        return has_more_items;
    }

    public void setHas_more_items(String has_more_items) {
        this.has_more_items = has_more_items;
    }

    public String getItems_html() {
        return items_html;
    }

    public void setItems_html(String items_html) {
        this.items_html = items_html;
    }

    public String getNew_latent_count() {
        return new_latent_count;
    }

    public void setNew_latent_count(String new_latent_count) {
        this.new_latent_count = new_latent_count;
    }
    
}
