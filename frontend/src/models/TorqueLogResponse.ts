import { ConfigDto } from "./ConfigDto";
import { ColumnDefinition } from "./ColumnDefinition";
import { EventDto } from "./EventDto";

export interface TorqueLogResponse {

  config: ConfigDto;

  columns: ColumnDefinition[];

  records: Record<string, any>[];

  events: EventDto[];
}