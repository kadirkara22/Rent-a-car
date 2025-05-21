"use client"
import { useState, useEffect } from 'react';
import Link from "next/link";
import { useCarContext } from '../../context/page';
const Header = () => {
 const { isLoggedIn} = useCarContext();
  return (
    <header style={styles.header}>
      <div style={styles.container}>
      
        <Link href="/">
         Rent-A-Car
        </Link>

        
        <nav style={styles.nav}>
          <ul style={styles.navList}>
            <li style={styles.navItem}>
              <Link href="/">
                Ana Sayfa
              </Link>
            </li>
            <li style={styles.navItem}>
              <Link href="/cars">
                Arabalar
              </Link>
            </li>
            <li style={styles.navItem}>
              <Link href="/about">
                Hakkımızda
              </Link>
            </li>
            <li style={styles.navItem}>
              <Link href="/contact">
                İletişim
              </Link>
            </li>
           
            {!isLoggedIn ? (
              <li style={{ ...styles.navItem, background: "grey", padding: "0 10px" }}>
                <Link href="/customer-login">
                  Giriş
                </Link>
              </li>
            ) : (
              <li style={styles.navItem}>
                <Link href="/profile">
                  Profilim
                </Link>
              </li>
            )}
          </ul>
        </nav>
      </div>
    </header>
  );
};


const styles = {
  header: {
    backgroundColor: '#333',
    color: '#fff',
    padding: '10px 20px',
    textAlign: 'center',
  },
  container: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  logo: {
    fontSize: '24px',
    fontWeight: 'bold',
    color: '#fff',
    textDecoration: 'none',
  },
  nav: {
    display: 'flex',
  },
  navList: {
    display: 'flex',
    listStyleType: 'none',
    padding: 0,
    margin: 0,
  },
  navItem: {
    margin: '0 10px',
  },
  navLink: {
    color: '#fff',
    textDecoration: 'none',
    fontSize: '18px',
  },
};

export default Header;
