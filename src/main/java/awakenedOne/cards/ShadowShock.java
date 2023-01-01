package awakenedOne.cards;

import awakenedOne.cards.tokens.Shadow;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static awakenedOne.AwakenedOneMod.makeID;

public class ShadowShock extends AbstractAwakenedCard implements OnObtainCard {
    public final static String ID = makeID(ShadowShock.class.getSimpleName());
    // intellij stuff attack, enemy, common, 9, 2, 9, 2, , 

    public ShadowShock() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        baseBlock = 9;
        cardsToPreview = new Shadow();
    }

    @Override
    public void onObtainCard() {
        AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Shadow(), Settings.WIDTH / 2, Settings.HEIGHT / 2));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}