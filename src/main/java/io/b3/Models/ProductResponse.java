package io.b3.Models;

import java.util.List;
import java.util.Map;

public class ProductResponse {
    private Hero hero;
    private Map<String, List<ProductDTO>> scrollArea;

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Map<String, List<ProductDTO>> getScrollArea() {
        return scrollArea;
    }

    public void setScrollArea(Map<String, List<ProductDTO>> scrollArea) {
        this.scrollArea = scrollArea;
    }

    public static class Hero {
        private String id;
        private String name;
        private String desc;
        private double curPrice;
        private List<String> preview;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public double getCurPrice() {
            return curPrice;
        }

        public void setCurPrice(double curPrice) {
            this.curPrice = curPrice;
        }

        public List<String> getPreview() {
            return preview;
        }

        public void setPreview(List<String> preview) {
            this.preview = preview;
        }
    }

    public static class ProductDTO {
        private String id;
        private String name;
        private String desc;
        private double curPrice;
        private List<String> preview;

        // Getters and setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public double getCurPrice() {
            return curPrice;
        }

        public void setCurPrice(double curPrice) {
            this.curPrice = curPrice;
        }

        public List<String> getPreview() {
            return preview;
        }

        public void setPreview(List<String> preview) {
            this.preview = preview;
        }
    }
}
