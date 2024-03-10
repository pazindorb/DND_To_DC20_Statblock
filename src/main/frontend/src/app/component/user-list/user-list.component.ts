import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'
import { User } from '../../model/user/user';
import { UserServiceService } from '../../service/user-service/user-service.service';
import { ActivatedRoute } from '@angular/router'

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent {
  users: User[];

  constructor(private userService: UserServiceService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
    });
  }
}
