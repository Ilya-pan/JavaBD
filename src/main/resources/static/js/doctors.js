// Переключение между вкладками
function openTab(tabId) {
  document.querySelectorAll('.tab-content').forEach(tab => {
    tab.classList.remove('active');
  });
  document.querySelectorAll('.tab-button').forEach(button => {
    button.classList.remove('active');
  });
  document.getElementById(tabId).classList.add('active');
  event.currentTarget.classList.add('active');
}

// Параметрический запрос - поиск по специализации
function searchBySpecialization() {
  const specialization = document.getElementById('specialization-search').value;
  if (!specialization) {
    alert('Введите специализацию для поиска');
    return;
  }

  fetch(`/doctors/by-specialization?specialization=${encodeURIComponent(specialization)}`)
    .then(response => response.text())
    .then(html => {
      const temp = document.createElement('div');
      temp.innerHTML = html;
      const doctorList = temp.querySelector('.doctor-list');
      if (doctorList) {
        document.getElementById('specialization-results').innerHTML = doctorList.innerHTML;
      } else {
        document.getElementById('specialization-results').innerHTML = '<p>Врачей с такой специализацией не найдено</p>';
      }
    })
    .catch(error => {
      console.error('Error:', error);
      document.getElementById('specialization-results').innerHTML = '<p>Ошибка при загрузке данных</p>';
    });
}

// Динамический запрос - фильтрация врачей
function applyDoctorFilters() {
  const filter = {
    fullName: document.getElementById('fullName').value || null,
    specialization: document.getElementById('specialization').value || null,
    licenseNumber: document.getElementById('licenseNumber').value || null
  };

  fetch('/doctors/filter', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(filter)
  })
  .then(response => response.json())
  .then(doctors => {
    updateDoctorList(doctors, 'filter-results');
  })
  .catch(error => {
    console.error('Error:', error);
    document.getElementById('filter-results').innerHTML = '<p>Ошибка при загрузке данных</p>';
  });
}

// Обновление списка врачей
function updateDoctorList(doctors, containerId) {
  const container = document.getElementById(containerId);
  container.innerHTML = '';

  if (!doctors || doctors.length === 0) {
    container.innerHTML = '<p>Врачей не найдено</p>';
    return;
  }

  doctors.forEach(doctor => {
    const doctorCard = document.createElement('div');
    doctorCard.className = 'doctor-card';
    doctorCard.innerHTML = `
      <h3>${doctor.fullName}</h3>
      <p>Специализация: ${doctor.specialization}</p>
      <p>Лицензия: ${doctor.licenseNumber}</p>
    `;
    container.appendChild(doctorCard);
  });
}
// ... существующий код ...

// Функция для удаления врача
function deleteDoctor(doctorId, doctorName) {
    if (confirm(`Вы уверены, что хотите удалить врача ${doctorName}?`)) {
        fetch(`/doctors/${doctorId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Врач успешно удален');
                // Обновляем список врачей
                if (document.getElementById('all-doctors').classList.contains('active')) {
                    loadAllDoctors();
                }
                // Можно добавить обновление других вкладок при необходимости
            } else {
                alert('Ошибка при удалении врача');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Ошибка при удалении врача');
        });
    }
}

// Функция для загрузки всех врачей (для обновления списка после удаления)
function loadAllDoctors() {
    fetch('/doctors')
        .then(response => response.text())
        .then(html => {
            const temp = document.createElement('div');
            temp.innerHTML = html;
            const doctorList = temp.querySelector('.doctor-list');
            if (doctorList) {
                document.querySelector('#all-doctors .doctor-list').innerHTML = doctorList.innerHTML;
                // Добавляем обработчики событий для новых кнопок удаления
                addDeleteHandlers();
            }
        });
}

// Добавление обработчиков событий для кнопок удаления
function addDeleteHandlers() {
    document.querySelectorAll('.delete-doctor-btn').forEach(button => {
        button.addEventListener('click', function() {
            const doctorId = this.getAttribute('data-doctor-id');
            const doctorName = this.getAttribute('data-doctor-name');
            deleteDoctor(doctorId, doctorName);
        });
    });
}

// Вызываем добавление обработчиков при загрузке страницы
document.addEventListener('DOMContentLoaded', addDeleteHandlers);
