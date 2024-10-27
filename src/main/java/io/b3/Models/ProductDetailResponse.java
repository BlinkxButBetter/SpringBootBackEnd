package io.b3.Models;

import java.util.List;

public class ProductDetailResponse {

    private ProductDTO product;
    private SellerDTO seller;

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }

    public static class ProductDTO {
        private String id;
        private String name;
        private String description;
        private double basePrice;
        private double maxPrice;
        private double currentPrice;
        private String status;
        private List<ImageDetail> images;


        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public double getBasePrice() { return basePrice; }
        public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

        public double getMaxPrice() { return maxPrice; }
        public void setMaxPrice(double maxPrice) { this.maxPrice = maxPrice; }

        public double getCurrentPrice() { return currentPrice; }
        public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public List<ImageDetail> getImages() { return images; }
        public void setImages(List<ImageDetail> images) { this.images = images; }
    }

    public static class SellerDTO {
        private String id;
        private String name;
        private String contact;


        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getContact() { return contact; }
        public void setContact(String contact) { this.contact = contact; }
    }

    public static class ImageDetail {
        private String url;
        private String altText;


        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public String getAltText() { return altText; }
        public void setAltText(String altText) { this.altText = altText; }
    }
}
