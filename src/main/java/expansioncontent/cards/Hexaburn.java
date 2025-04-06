package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import expansioncontent.expansionContentMod;
import hermit.vfx.ShortScreenFire;

public class Hexaburn extends AbstractExpansionCard {
    public static final String ID = makeID("Hexaburn");

    public Hexaburn() {
        super(ID, 2, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_hexaghost.png", "expansioncontentResources/images/1024/bg_boss_hexaghost.png");
        this.tags.add(expansionContentMod.STUDY_HEXAGHOST);
        this.tags.add(expansionContentMod.STUDY);
        this.baseMagicNumber = this.magicNumber = 6;
        this.baseDamage = 4;
        this.exhaust = true;
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "Hexaburn.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShortScreenFire(), 0.5F));
        for (int i = 0; i < this.magicNumber; i++)
            addToBot((AbstractGameAction)new AttackDamageRandomEnemyAction((AbstractCard)this, AbstractGameAction.AttackEffect.FIRE));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
