import { BaseEntity } from './../../shared';

export class BuildingData implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public label?: string,
        public building?: BaseEntity,
    ) {
    }
}
