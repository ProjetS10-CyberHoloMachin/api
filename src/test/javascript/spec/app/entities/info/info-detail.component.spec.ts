/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MicroTestModule } from '../../../test.module';
import { InfoDetailComponent } from '../../../../../../main/webapp/app/entities/info/info-detail.component';
import { InfoService } from '../../../../../../main/webapp/app/entities/info/info.service';
import { Info } from '../../../../../../main/webapp/app/entities/info/info.model';

describe('Component Tests', () => {

    describe('Info Management Detail Component', () => {
        let comp: InfoDetailComponent;
        let fixture: ComponentFixture<InfoDetailComponent>;
        let service: InfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [InfoDetailComponent],
                providers: [
                    InfoService
                ]
            })
            .overrideTemplate(InfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Info(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.info).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
