import { BaseEntity } from './../../shared';

export class Affectation implements BaseEntity {
    constructor(
        public id?: number,
        public notificationId?: number,
        public userId?: number,
    ) {
    }
}
