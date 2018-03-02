import { BaseEntity } from './../../shared';

export class BuildingDataDefinition implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
    ) {
    }
}
