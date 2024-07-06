package com.phuc.photoappwebclients.controller;


import com.phuc.photoappwebclients.response.AlbumsRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Arrays;
import java.util.List;

@Controller
public class AlbumsController {

    @Autowired
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService;


//    @Autowired
//    RestTemplate restTemplate;

    @Autowired
    WebClient webClient;

    @GetMapping("/albums")
    public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {
        // Comment code, thay the webclient
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//
//        OAuth2AuthorizedClient oauth2Client = oAuth2AuthorizedClientService.loadAuthorizedClient(
//                oauthToken.getAuthorizedClientRegistrationId(),
//                principal.getName());
//
//        String tokenValue = oauth2Client.getAccessToken().getTokenValue();
//        System.out.println("Access Token: " + tokenValue);
//
//        System.out.println("Principal = " + principal);
//
//        String oidcIdToken = principal.getIdToken().getTokenValue();
//        System.out.println("idTokenValue = " + oidcIdToken);

        //  Example Webflux, ho tro authorization
        String url = "http://127.0.0.1:8082/ablum";
        List<AlbumsRest> albums = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<AlbumsRest>>() {
                })
                .block();

        model.addAttribute("albums", albums);

        //  Example RestTemplate
//        String url = "http://127.0.0.1:8082/ablum";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + tokenValue);
//
//        HttpEntity entity = new HttpEntity<>(headers);
//
//        ResponseEntity<List<AlbumsRest>> responseEntity = restTemplate.exchange(url,
//                HttpMethod.GET,
//                entity,
//                new ParameterizedTypeReference<List<AlbumsRest>>() {
//                }
//        );
//
//        List<AlbumsRest> ablums = responseEntity.getBody();
//        model.addAttribute("albums", ablums);

        AlbumsRest albumsRest = new AlbumsRest();
        albumsRest.setAlbumId("albumOne");
        albumsRest.setAlbumTitle("One Title");
        albumsRest.setAlbumUrl("http://localhost:8082/albums/1");

        AlbumsRest albumsRest1 = new AlbumsRest();
        albumsRest1.setAlbumId("albumTwo");
        albumsRest1.setAlbumTitle("Two Title");
        albumsRest1.setAlbumUrl("http://localhost:8082/albums/2");

        model.addAttribute("albums", Arrays.asList(albumsRest,albumsRest1));

        return "albums";
    }
}
