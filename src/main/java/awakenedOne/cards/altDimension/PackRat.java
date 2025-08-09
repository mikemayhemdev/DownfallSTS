package awakenedOne.cards.altDimension;

import awakenedOne.AwakenedOneMod;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

import awakenedOne.cards.AbstractAwakenedCard;
import static awakenedOne.util.Wiz.atb;

@NoCompendium
@NoPools
public class PackRat extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(PackRat.class.getSimpleName());
    public PackRat() {
        super(ID, 1, CardRarity.RARE, CardType.ATTACK, CardTarget.ENEMY);
        baseDamage = 2;
        baseBlock = 2;
        exhaust = true;

        frameString = "inscryp";
        this.setBackgroundTexture("awakenedResources/images/512/dimension/" + frameString + "/" + getTypeName() + ".png",       "awakenedResources/images/1024/dimension/" + frameString + "/" + getTypeName() + ".png");

    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));// 117
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        blck();
        atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));// 117
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        blck();

        this.addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}