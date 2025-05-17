package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import expansioncontent.expansionContentMod;

public class GuardianWhirl extends AbstractExpansionCard {
    public static final String ID = makeID("GuardianWhirl");

    private static final int DAMAGE = 5;

    private static final int MAGIC = 2;

    private static final int downfallMagic = 10;

    public GuardianWhirl() {
        super(ID, 2, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_guardian.png", "expansioncontentResources/images/1024/bg_boss_guardian.png");
        this.tags.add(expansionContentMod.STUDY_GUARDIAN);
        this.tags.add(expansionContentMod.STUDY);
        this.baseDamage = 5;
        this.isMultiDamage = true;
        this.baseDownfallMagic = 10;
        this.magicNumber = this.baseMagicNumber = 2;
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "GuardianWhirl.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb((AbstractGameAction)new SFXAction("ATTACK_WHIRLWIND"));
        int i;
        for (i = 0; i < this.magicNumber; i++) {
            atb((AbstractGameAction)new SFXAction("ATTACK_HEAVY"));
            atb((AbstractGameAction)new VFXAction((AbstractCreature)p, (AbstractGameEffect)new CleaveEffect(), 0.1F));
            atb((AbstractGameAction)new DamageAllEnemiesAction((AbstractCreature)p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }
        if (p.currentBlock >= 10)
            for (i = 0; i < this.magicNumber; i++) {
                atb((AbstractGameAction)new SFXAction("ATTACK_HEAVY"));
                atb((AbstractGameAction)new VFXAction((AbstractCreature)p, (AbstractGameEffect)new CleaveEffect(), 0.1F));
                atb((AbstractGameAction)new DamageAllEnemiesAction((AbstractCreature)p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
            }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
        }
    }
}
