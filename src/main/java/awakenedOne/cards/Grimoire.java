package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.AphoticShield;
import awakenedOne.relics.EyeOfTheOccult;
import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.ui.OrbitingSpells.updateTimeOffsets;
import static awakenedOne.util.Wiz.vfx;

public class Grimoire extends AbstractAwakenedCard {
    public final static String ID = makeID(Grimoire.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 9, 1, , , 3, 1

    public Grimoire() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.selfRetain = true;
        baseDamage = 6;
        baseMagicNumber = magicNumber = 6;
        loadJokeCardImage(this, makeBetaCardPath(Grimoire.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        this.addToTop(new ModifyDamageAction(this.uuid, this.magicNumber));
        spellCards.add(new OrbitingSpells.CardRenderInfo(this));
        p.hand.removeCard(this);
        p.drawPile.removeCard(this);
        p.discardPile.removeCard(this);
        p.limbo.removeCard(this);
        updateTimeOffsets();
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}