package sn.cfpp.pfe.pfeUGB.security.cottroller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping("/uploadClientImage/{clientId}")
    public ResponseEntity<String> uploadClientImage(@PathVariable int clientId, @RequestParam("file") MultipartFile file) throws IOException {
        // Créer le répertoire pour stocker l'image s'il n'existe pas
        File dir = new File(uploadDir + "/client/" + clientId);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Enregistrer l'image sur le disque
        Path path = Paths.get(dir.getPath(), file.getOriginalFilename());
        Files.write(path, file.getBytes());

        // Retourner le chemin de l'image
        return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully: " + path.toString());
    }

    @PostMapping("/uploadLivreurImage/{livreurId}")
    public ResponseEntity<String> uploadLivreurImage(@PathVariable int livreurId, @RequestParam("file") MultipartFile file) throws IOException {
        // Créer le répertoire pour stocker l'image s'il n'existe pas
        File dir = new File(uploadDir + "/livreur/" + livreurId);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Enregistrer l'image sur le disque
        Path path = Paths.get(dir.getPath(), file.getOriginalFilename());
        Files.write(path, file.getBytes());

        // Retourner le chemin de l'image
        return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully: " + path.toString());
    }

    public String uploadClientImage(Long idCl, MultipartFile imageFile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uploadClientImage'");
    }

    public String uploadLivreurImage(Long idLivreur, MultipartFile imageFile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uploadLivreurImage'");
    }

    // Vous pouvez également ajouter une méthode pour récupérer l'image si nécessaire
}

