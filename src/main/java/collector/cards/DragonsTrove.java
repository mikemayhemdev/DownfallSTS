package collector.cards;

import collector.CollectorCollection;
import collector.actions.DrawCardFromCollectionAction;
import collector.actions.GainReservesAction;
import collector.relics.HolidayCoal;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static hermit.util.Wiz.att;

public class DragonsTrove extends AbstractCollectorCard {
    public final static String ID = makeID(DragonsTrove.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , 2, 1

    public DragonsTrove() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
        isPyre();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("MAW_DEATH");
        for (int i = 0; i < magicNumber; i++) {
            att(new DrawCardFromCollectionAction());
            if (!CollectorCollection.combatCollection.isEmpty()) {
                atb(new DrawCardAction(1));
            }
        }
        atb(new GainReservesAction(1));
    }

    public void upp() {
        upgradeMagicNumber(1);
        uDesc();
    }
}