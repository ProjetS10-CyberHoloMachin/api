/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CyberholocampusTestModule } from '../../../test.module';
import { NotificationDataComponent } from '../../../../../../main/webapp/app/entities/notification-data/notification-data.component';
import { NotificationDataService } from '../../../../../../main/webapp/app/entities/notification-data/notification-data.service';
import { NotificationData } from '../../../../../../main/webapp/app/entities/notification-data/notification-data.model';

describe('Component Tests', () => {

    describe('NotificationData Management Component', () => {
        let comp: NotificationDataComponent;
        let fixture: ComponentFixture<NotificationDataComponent>;
        let service: NotificationDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyberholocampusTestModule],
                declarations: [NotificationDataComponent],
                providers: [
                    NotificationDataService
                ]
            })
            .overrideTemplate(NotificationDataComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotificationDataComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotificationDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NotificationData(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.notificationData[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
