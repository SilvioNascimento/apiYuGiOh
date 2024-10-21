package br.ufpb.dcx.dsc.apiYuGiOh.controller;

import br.ufpb.dcx.dsc.apiYuGiOh.dto.CardDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.dto.CardMonsterDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.dto.CardSpellDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.dto.CardTrapDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Card;
import br.ufpb.dcx.dsc.apiYuGiOh.model.CardMonster;
import br.ufpb.dcx.dsc.apiYuGiOh.model.CardSpell;
import br.ufpb.dcx.dsc.apiYuGiOh.model.CardTrap;
import br.ufpb.dcx.dsc.apiYuGiOh.service.CardService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@Validated
public class CardController {

    private final ModelMapper modelMapper;
    private final CardService cardService;

    public CardController(ModelMapper modelMapper, CardService cardService) {
        this.modelMapper = modelMapper;
        this.cardService = cardService;
    }

    @GetMapping("/cards")
    public List<CardDTO> listCards() {
        return cardService.listCards().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/cards/{cardId}")
    public CardDTO getCard(@PathVariable Long cardId) {
        Card card = cardService.getCard(cardId);
        System.out.println(card.toString());
        return convertToDTO(card);
    }


    @PostMapping("/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public CardDTO createMonsterCard(@Valid @RequestBody CardDTO cardDTO) {
        Card card = convertToEntity(cardDTO);
        Card saved = cardService.createCard(card);
        return convertToDTO(saved);
    }

//    @PostMapping("/cards/monster")
//    @ResponseStatus(HttpStatus.CREATED)
//    public CardMonsterDTO createMonsterCard(@RequestBody CardMonsterDTO cardMonsterDTO) {
//        Card card = convertToEntity(cardMonsterDTO);
//        Card saved = cardService.createCard(card);
//        return (CardMonsterDTO) convertToDTO(saved);
//    }
//
//    @PostMapping("/cards/spell")
//    @ResponseStatus(HttpStatus.CREATED)
//    public CardSpellDTO createSpellCard(@RequestBody CardSpellDTO cardSpellDTO) {
//        Card card = convertToEntity(cardSpellDTO);
//        Card saved = cardService.createCard(card);
//        return (CardSpellDTO) convertToDTO(saved);
//    }
//
//    @PostMapping("/cards/trap")
//    @ResponseStatus(HttpStatus.CREATED)
//    public CardTrapDTO createTrapCard(@RequestBody CardTrapDTO cardTrapDTO) {
//        Card card = convertToEntity(cardTrapDTO);
//        Card saved = cardService.createCard(card);
//        return (CardTrapDTO) convertToDTO(saved);
//    }

    @PutMapping("/cards/{cardId}")
    public CardDTO updateCard(@PathVariable Long cardId, @Valid @RequestBody CardDTO cardDTO) {
        Card card = convertToEntity(cardDTO);
        Card updatedCard = cardService.updateCard(cardId, card);
        return convertToDTO(updatedCard);
    }

    @PutMapping("cards/{cardId}/photo/{photoId}")
    public CardDTO addPhotoInCard(@PathVariable Long cardId, @PathVariable Long photoId){
        return convertToDTO(cardService.addPhotoInCard(cardId, photoId));
    }

    @PutMapping("cards/{cardId}/photo/{photoId}/remove")
    public CardDTO removePhotoInCard(@PathVariable Long cardId, @PathVariable Long photoId){
        return convertToDTO(cardService.removePhotoInCard(cardId, photoId));
    }

    @DeleteMapping("/cards/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
    }

    // Conversão entre entidades e DTOs
    private CardDTO convertToDTO(Card card) {
        System.out.println("Card type: " + card.getClass().getSimpleName());
        if (card instanceof CardMonster) {
            return modelMapper.map(card, CardMonsterDTO.class);
        } else if (card instanceof CardSpell) {
            return modelMapper.map(card, CardSpellDTO.class);
        } else if (card instanceof CardTrap) {
            return modelMapper.map(card, CardTrapDTO.class);
        } else {
            throw new IllegalArgumentException("Tipo de card desconhecido");
        }
    }

    private Card convertToEntity(CardDTO cardDTO) {
        if (cardDTO instanceof CardMonsterDTO) {
            return modelMapper.map(cardDTO, CardMonster.class);
        } else if (cardDTO instanceof CardSpellDTO) {
            return modelMapper.map(cardDTO, CardSpell.class);
        } else if (cardDTO instanceof CardTrapDTO) {
            return modelMapper.map(cardDTO, CardTrap.class);
        } else {
            throw new IllegalArgumentException("Tipo de DTO desconhecido");
        }
    }
}

