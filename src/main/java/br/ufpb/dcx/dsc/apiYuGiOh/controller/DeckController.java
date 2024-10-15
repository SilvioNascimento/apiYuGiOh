package br.ufpb.dcx.dsc.apiYuGiOh.controller;

import br.ufpb.dcx.dsc.apiYuGiOh.dto.DeckDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.model.Deck;
import br.ufpb.dcx.dsc.apiYuGiOh.service.DeckService;
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
public class DeckController {

    private final ModelMapper modelMapper;
    private final DeckService deckService;

    public DeckController(ModelMapper modelMapper, DeckService deckService) {
        this.modelMapper = modelMapper;
        this.deckService = deckService;
    }

    @GetMapping("/deck")
    public List<DeckDTO> listDecks() {
        return deckService.listDecks().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/deck/{deckId}")
    public DeckDTO getDeck(@PathVariable Long deckId) {
        Deck deck = deckService.getDeck(deckId);
        return convertToDTO(deck);
    }

    @PostMapping("/deck")
    @ResponseStatus(HttpStatus.CREATED)
    public DeckDTO createDeck(@Valid @RequestBody DeckDTO deckDTO) {
        Deck deck = convertToEntity(deckDTO);
        Deck saved = deckService.createDeck(deck);
        return convertToDTO(saved);
    }

    @PutMapping("/deck/{deckId}")
    public DeckDTO updateDeck(@PathVariable Long deckId, @Valid @RequestBody DeckDTO deckDTO) {
        Deck deck = convertToEntity(deckDTO);
        Deck updateDeck = deckService.updateDeck(deckId, deck);
        return convertToDTO(updateDeck);
    }

    @DeleteMapping("/deck/{deckId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeck(@PathVariable Long deckId) {
        deckService.deleteDeck(deckId);
    }

    private DeckDTO convertToDTO(Deck deck) {
        return modelMapper.map(deck, DeckDTO.class);
    }

    private Deck convertToEntity(DeckDTO deckDTO) {
        return modelMapper.map(deckDTO, Deck.class);
    }
}
