package com.blueapps.glyphconvert;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.blueapps.glpyhconverter.GlyphConverter;
import com.blueapps.glyphconvert.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.inputGlyphx.setText(R.string.default_glyphx);
        binding.inputMdc.setText(R.string.default_mdc);

        binding.convertToGlyphx.setOnClickListener(view -> {
            String glyphX = GlyphConverter.convertToGlyphX(String.valueOf(binding.inputMdc.getText()));
            binding.inputGlyphx.setText(glyphX);
        });

        binding.convertToMdc.setOnClickListener(view -> {
            String MdC = GlyphConverter.convertToMdC(String.valueOf(binding.inputGlyphx.getText()));
            binding.inputMdc.setText(MdC);
        });
    }
}