import { BaseEntity } from './../../shared';

export class Info implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public definitionId?: number,
        public notificationId?: number,
    ) {
    }
}
