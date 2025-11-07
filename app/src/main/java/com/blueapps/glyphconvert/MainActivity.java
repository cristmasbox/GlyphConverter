package com.blueapps.glyphconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.blueapps.glpyhconverter.GlyphConverter;
import com.blueapps.glyphconvert.databinding.ActivityMainBinding;

import java.util.Arrays;

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
            String glyphX = "";
            try {
                glyphX = GlyphConverter.convertToGlyphX(String.valueOf(binding.inputMdc.getText()));
            } catch (Exception e){
                e.printStackTrace();
                binding.errorText.setText(e.getLocalizedMessage());
            }
            binding.inputGlyphx.setText(glyphX);
        });

        binding.convertToMdc.setOnClickListener(view -> {
            String MdC = "";
            try {
                MdC = GlyphConverter.convertToMdC(String.valueOf(binding.inputGlyphx.getText()));
            } catch (Exception e){
                e.printStackTrace();
                binding.errorText.setText(e.getLocalizedMessage());
            }
            binding.inputMdc.setText(MdC);
        });

        binding.cleanMdc.setOnClickListener(view -> {
            String MdC = "";
            try {
                MdC = GlyphConverter.cleanMdC(String.valueOf(binding.inputMdc.getText()));
            } catch (Exception e){
                e.printStackTrace();
                binding.errorText.setText(e.getLocalizedMessage());
            }
            binding.correctedMdcText.setText(MdC);
        });
    }
}