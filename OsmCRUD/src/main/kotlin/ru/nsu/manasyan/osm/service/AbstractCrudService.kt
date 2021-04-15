package ru.nsu.manasyan.osm.service

import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.nsu.manasyan.osm.model.entity.Identifiable
import ru.nsu.manasyan.osm.util.PaginationRequestParams
import ru.nsu.manasyan.osm.util.LoggerProperty
import ru.nsu.manasyan.osm.util.getPageableOrDefault

@Service
abstract class AbstractCrudService<E : Identifiable>(
    protected open val repository: JpaRepository<E, Long>
) {
    private val logger by LoggerProperty()

    protected abstract val entityName: String

    fun getAll(parameters: PaginationRequestParams?): Page<E> {
        return repository.findAll(
            getPageableOrDefault(parameters)
        )
    }

    fun add(entity: E) {
        repository.save(entity).let {
            logger.debug("$entityName #{${it.id}} was added")
        }
    }

    fun get(id: Long): E {
        return repository.findById(id).orElseThrow {
            IllegalArgumentException("Wrong id: $id")
        }
    }

    @Transactional
    fun delete(id: Long) {
        return if (repository.existsById(id)) {
            repository.deleteById(id)
            logger.debug("$entityName #{$id} was deleted")
        } else throw IllegalArgumentException("Wrong id to delete: $id")
    }

    @Transactional
    fun update(entity: E) {
        entity.id
            ?.let { id ->
                if (repository.existsById(id)) {
                    repository.save(entity)
                    logger.debug("$entityName #{$id} was updated")
                } else throw IllegalArgumentException("Wrong id: $id")
            }
            ?: throw IllegalArgumentException("Id required")
    }
}
