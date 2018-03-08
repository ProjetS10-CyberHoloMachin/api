/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CyberholocampusTestModule } from '../../../test.module';
import { NotificationDataDetailComponent } from '../../../../../../main/webapp/app/entities/notification-data/notification-data-detail.component';
import { NotificationDataService } from '../../../../../../main/webapp/app/entities/notification-data/notification-data.service';
import { NotificationData } from '../../../../../../main/webapp/app/entities/notification-data/notification-data.model';

describe('Component Tests', () => {

    describe('NotificationData Management Detail Component', () => {
        let comp: NotificationDataDetailComponent;
        let fixture: ComponentFixture<NotificationDataDetailComponent>;
        let service: NotificationDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyberholocampusTestModule],
                declarations: [NotificationDataDetailComponent],
                providers: [
                    NotificationDataService
                ]
            })
            .overrideTemplate(NotificationDataDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotificationDataDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotificationDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NotificationData(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.notificationData).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
