//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package evilWithin.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import evilWithin.monsters.FleeingMerchant;
import evilWithin.patches.EvilModeCharacterSelect;
import evilWithin.util.HeartMerchant;

import java.util.Iterator;

public class HeartShopRoom extends ShopRoom {
    public HeartMerchant merchant;

    public HeartShopRoom() {
        super();
    }

    @Override
    public void setMerchant(Merchant merc) {
        super.setMerchant(merc);
    }

    public void setHeartMerchant(HeartMerchant merc) {
        merchant = merc;
    }

    public void onPlayerEntry() {
        if (EvilModeCharacterSelect.evilMode)
            if (!FleeingMerchant.DEAD)
                startCombat();
            else
                ((HeartShopRoom) AbstractDungeon.getCurrRoom()).merchant = new HeartMerchant();

        if (!AbstractDungeon.id.equals("TheEnding")) {
            this.playBGM("SHOP");
        }

        AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);
        this.setHeartMerchant(new HeartMerchant());
    }


    private static void startCombat() {
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
        AbstractDungeon.lastCombatMetricKey = FleeingMerchant.ID;
        AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new FleeingMerchant());

        AbstractDungeon.getCurrRoom().rewards.clear();

        AbstractDungeon.getCurrRoom().monsters.init();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            m.usePreBattleAction();
            m.useUniversalPreBattleAction();
        }
        ((HeartShopRoom) AbstractDungeon.getCurrRoom()).merchant = new HeartMerchant();
        AbstractRoom.waitTimer = 0.1f;
        AbstractDungeon.player.preBattlePrep();
    }

}
