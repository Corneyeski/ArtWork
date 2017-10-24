package artwork.web.rest;

import com.codahale.metrics.annotation.Timed;
import artwork.domain.Conversation;

import artwork.repository.ConversationRepository;
import artwork.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Conversation.
 */
@RestController
@RequestMapping("/api")
public class ConversationResource {

    private final Logger log = LoggerFactory.getLogger(ConversationResource.class);

    private static final String ENTITY_NAME = "conversation";

    private final ConversationRepository conversationRepository;

    public ConversationResource(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    /**
     * POST  /conversations : Create a new conversation.
     *
     * @param conversation the conversation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conversation, or with status 400 (Bad Request) if the conversation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conversations")
    @Timed
    public ResponseEntity<Conversation> createConversation(@RequestBody Conversation conversation) throws URISyntaxException {
        log.debug("REST request to save Conversation : {}", conversation);
        if (conversation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new conversation cannot already have an ID")).body(null);
        }
        Conversation result = conversationRepository.save(conversation);
        return ResponseEntity.created(new URI("/api/conversations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conversations : Updates an existing conversation.
     *
     * @param conversation the conversation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conversation,
     * or with status 400 (Bad Request) if the conversation is not valid,
     * or with status 500 (Internal Server Error) if the conversation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conversations")
    @Timed
    public ResponseEntity<Conversation> updateConversation(@RequestBody Conversation conversation) throws URISyntaxException {
        log.debug("REST request to update Conversation : {}", conversation);
        if (conversation.getId() == null) {
            return createConversation(conversation);
        }
        Conversation result = conversationRepository.save(conversation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conversation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conversations : get all the conversations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of conversations in body
     */
    @GetMapping("/conversations")
    @Timed
    public List<Conversation> getAllConversations() {
        log.debug("REST request to get all Conversations");
        return conversationRepository.findAll();
        }

    /**
     * GET  /conversations/:id : get the "id" conversation.
     *
     * @param id the id of the conversation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conversation, or with status 404 (Not Found)
     */
    @GetMapping("/conversations/{id}")
    @Timed
    public ResponseEntity<Conversation> getConversation(@PathVariable Long id) {
        log.debug("REST request to get Conversation : {}", id);
        Conversation conversation = conversationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(conversation));
    }

    /**
     * DELETE  /conversations/:id : delete the "id" conversation.
     *
     * @param id the id of the conversation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conversations/{id}")
    @Timed
    public ResponseEntity<Void> deleteConversation(@PathVariable Long id) {
        log.debug("REST request to delete Conversation : {}", id);
        conversationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
