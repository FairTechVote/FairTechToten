package com.example.fairtechtoten.features.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fairtechtoten.databinding.FragmentMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private Marker currentLocationMarker;
    private Marker destinationMarker;
    private Polyline routePolyline;

    private final LatLng destination = new LatLng(-3.1190, -60.0217);

    // ---- Permissão de localização ----
    private final ActivityResultLauncher<String[]> locationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                Boolean fineGranted = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseGranted = permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);

                if (Boolean.TRUE.equals(fineGranted) || Boolean.TRUE.equals(coarseGranted)) {
                    enableLocationAndStart();
                } else {
                    Toast.makeText(requireContext(), "Permissão de localização negada", Toast.LENGTH_LONG).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(com.example.fairtechtoten.R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


        binding.btnRoute.setOnClickListener(v -> {
            if (hasLocationPermission()) {
                fetchCurrentLocationAndRoute();
            } else {
                requestLocationPermission();
            }
        });

        binding.btnMyLocation.setOnClickListener(v -> centerOnUserLocation());
    }


    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);


        destinationMarker = googleMap.addMarker(new MarkerOptions()
                .position(destination)
                .title("Destino: Arena da Amazônia")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 13f));

        if (hasLocationPermission()) {
            enableLocationAndStart();
        } else {
            requestLocationPermission();
        }
    }

    // ---- Permissões ----
    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        locationPermissionLauncher.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }


    @SuppressLint("MissingPermission")
    private void enableLocationAndStart() {
        googleMap.setMyLocationEnabled(true);

        LocationRequest request = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L)
                .setMinUpdateDistanceMeters(5f)
                .build();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult result) {
                Location location = result.getLastLocation();
                if (location != null) {
                    updateUserMarker(location);
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper());

        fetchCurrentLocationAndRoute();
    }

    @SuppressLint("MissingPermission")
    private void centerOnUserLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
            }
        });
    }

    private void updateUserMarker(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        if (currentLocationMarker == null) {
            currentLocationMarker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Você está aqui")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        } else {
            currentLocationMarker.setPosition(latLng);
        }
    }


    @SuppressLint("MissingPermission")
    private void fetchCurrentLocationAndRoute() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnRoute.setEnabled(false);

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location == null) {
                binding.progressBar.setVisibility(View.GONE);
                binding.btnRoute.setEnabled(true);
                Toast.makeText(requireContext(), "Não foi possível obter sua localização", Toast.LENGTH_SHORT).show();
                return;
            }

            LatLng origin = new LatLng(location.getLatitude(), location.getLongitude());
            drawRoute(origin, destination);
        });
    }

    private void drawRoute(LatLng origin, LatLng dest) {
        String apiKey = "SUA_API_KEY_AQUI";

        String url = "https://maps.googleapis.com/maps/api/directions/json"
                + "?origin=" + origin.latitude + "," + origin.longitude
                + "&destination=" + dest.latitude + "," + dest.longitude
                + "&mode=driving"
                + "&language=pt-BR"
                + "&key=" + apiKey;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String json = fetchUrl(url);

            handler.post(() -> {
                if (json == null) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.btnRoute.setEnabled(true);
                    Toast.makeText(requireContext(), "Erro ao buscar rota", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<LatLng> points = parseDirectionsResponse(json);

                if (!points.isEmpty()) {
                    drawPolyline(points);
                    fitCameraToRoute(origin, dest);

                    String distanceText = extractDistance(json);
                    String durationText = extractDuration(json);
                    binding.tvRouteInfo.setText("📍 " + distanceText + "  •  ⏱ " + durationText);
                    binding.tvRouteInfo.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(requireContext(), "Não foi possível calcular a rota", Toast.LENGTH_SHORT).show();
                }

                binding.progressBar.setVisibility(View.GONE);
                binding.btnRoute.setEnabled(true);
            });
        });
    }

    private String fetchUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<LatLng> parseDirectionsResponse(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray routes = jsonObj.getJSONArray("routes");
            if (routes.length() == 0) return java.util.Collections.emptyList();

            String overviewPolyline = routes
                    .getJSONObject(0)
                    .getJSONObject("overview_polyline")
                    .getString("points");

            return PolyUtil.decode(overviewPolyline);
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }

    private String extractDistance(String json) {
        try {
            return new JSONObject(json)
                    .getJSONArray("routes").getJSONObject(0)
                    .getJSONArray("legs").getJSONObject(0)
                    .getJSONObject("distance")
                    .getString("text");
        } catch (Exception e) {
            return "";
        }
    }

    private String extractDuration(String json) {
        try {
            return new JSONObject(json)
                    .getJSONArray("routes").getJSONObject(0)
                    .getJSONArray("legs").getJSONObject(0)
                    .getJSONObject("duration")
                    .getString("text");
        } catch (Exception e) {
            return "";
        }
    }


    private void drawPolyline(List<LatLng> points) {
        if (routePolyline != null) routePolyline.remove();
        routePolyline = googleMap.addPolyline(new PolylineOptions()
                .addAll(points)
                .color(Color.parseColor("#1976D2"))
                .width(12f)
                .geodesic(true));
    }

    private void fitCameraToRoute(LatLng origin, LatLng dest) {
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(dest)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
    }


    @Override
    public void onPause() {
        super.onPause();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasLocationPermission() && locationCallback != null) {
            enableLocationAndStart();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}