package com.glowcart.app.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.glowcart.app.R;
import com.glowcart.app.models.CartItemModel;
import com.glowcart.app.utils.CartManager;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView btnBack, btnWishlist, imgProduct;
    TextView tvBrand, tvProductName, tvPrice, tvOldPrice, tvRating, tvDescription;

    boolean isWishlisted = false;

    // ✅ product details
    Long productId;
    String name, brand, price, oldPrice, rating, description, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        imgProduct = findViewById(R.id.imgProduct);
        tvBrand = findViewById(R.id.tvBrand);
        tvProductName = findViewById(R.id.tvProductName);
        tvPrice = findViewById(R.id.tvPrice);
        tvOldPrice = findViewById(R.id.tvOldPrice);
        tvRating = findViewById(R.id.tvRating);
        tvDescription = findViewById(R.id.tvDescription);

        btnBack = findViewById(R.id.btnBack);
        btnWishlist = findViewById(R.id.btnWishlist);

        // ✅ intent
        Intent intent = getIntent();

        // ✅ get productId also
        if (intent.hasExtra("id")) {
            productId = intent.getLongExtra("id", 0);
        } else {
            productId = 0L;
        }

        name = intent.getStringExtra("name");
        brand = intent.getStringExtra("brand");
        price = intent.getStringExtra("price");
        oldPrice = intent.getStringExtra("oldPrice");
        rating = intent.getStringExtra("rating");
        description = intent.getStringExtra("description");
        imageUrl = intent.getStringExtra("imageUrl");

        tvProductName.setText(name);
        tvBrand.setText(brand);
        tvPrice.setText(price);
        tvOldPrice.setText(oldPrice);
        tvRating.setText(rating);
        tvDescription.setText(description);

        tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        // ✅ Image load
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.prod1)
                .error(R.drawable.prod1)
                .into(imgProduct);

        btnBack.setOnClickListener(v -> finish());

        // Wishlist toggle
        btnWishlist.setOnClickListener(v -> {
            isWishlisted = !isWishlisted;

            btnWishlist.setImageResource(
                    isWishlisted
                            ? android.R.drawable.btn_star_big_on
                            : android.R.drawable.btn_star_big_off
            );

            Toast.makeText(
                    this,
                    isWishlisted ? "Added to Wishlist" : "Removed from Wishlist",
                    Toast.LENGTH_SHORT
            ).show();
        });

        // ✅ Add to cart
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(v -> {

            int priceInt = parsePrice(price);

            // ✅ get product id from intent
            Long productId = getIntent().hasExtra("id") ? getIntent().getLongExtra("id", 0) : 0;

            CartItemModel cartItem = new CartItemModel(
                    productId,
                    name,
                    brand,
                    priceInt,
                    1,
                    imageUrl
            );

            CartManager.addToCart(cartItem);

            Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Buy now
        findViewById(R.id.btnBuyNow).setOnClickListener(v ->
                Toast.makeText(this, "Proceeding to Checkout", Toast.LENGTH_SHORT).show()
        );
    }

    private int parsePrice(String priceText) {
        if (priceText == null) return 0;
        priceText = priceText.replace("₹", "").replace(",", "").trim();
        try {
            return Integer.parseInt(priceText);
        } catch (Exception e) {
            return 0;
        }
    }
}