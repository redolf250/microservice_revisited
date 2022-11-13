package org.itech.redolf.service;

import org.itech.redolf.model.DatabaseSequence;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceService {

    private final MongoOperations mongoOperations;

    public SequenceService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public int getSequenceService(String sequence_name) {
       Query query = new Query(Criteria.byExample("_id").is(sequence_name));
       Update update = new Update().inc("sequence_number", 1);
       DatabaseSequence counter = mongoOperations.findAndModify(query,update,
               options().returnNew(true).upsert(true),
               DatabaseSequence.class);
       return !Objects.isNull(counter) ? counter.getSequence_number() : 1;
   }
}
