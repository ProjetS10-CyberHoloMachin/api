import { BaseEntity } from './../../shared';

export class NotificationData implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public description?: string,
        public notification?: BaseEntity,
    ) {
    }
}
