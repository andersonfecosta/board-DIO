package dto;

import java.time.OffsetDateTime;

public record CardDetailsDTO(Long id,
                             boolean blocked,
                             OffsetDateTime blockAt,
                             String blockReason,
                             int blocksAmount,
                             Long columnId,
                             String ColumnName
) {
}
