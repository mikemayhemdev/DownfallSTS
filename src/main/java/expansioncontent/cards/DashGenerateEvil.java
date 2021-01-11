package expansioncontent.cards;


import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import expansioncontent.actions.RandomCardWithTagAction;
import expansioncontent.expansionContentMod;


public class DashGenerateEvil extends AbstractExpansionCard {
    public final static String ID = makeID("DashGenerateEvil");

    private static final int BLOCK = 10;
    private static final int UPGRADE_BLOCK = 2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_DAMAGE = 2;

    private float rotationTimer;
    private int previewIndex;

    public DashGenerateEvil() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_attack.png", "expansioncontentResources/images/1024/bg_boss_attack.png");
        baseBlock = BLOCK;
        baseDamage = DAMAGE;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        this.addToBot(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));

        atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        atb(new GainBlockAction(p, p, this.block));

        atb(new RandomCardWithTagAction(upgraded, expansionContentMod.STUDY, true));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeDamage(UPGRADE_DAMAGE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                if (!upgraded) {
                    rotationTimer = 2F;
                    if (QuickStudy.allStudyCardsList.size() == 0) {
                        cardsToPreview = CardLibrary.cards.get("Madness");
                    } else {
                        cardsToPreview = QuickStudy.allStudyCardsList.get(previewIndex);
                    }
                    if (previewIndex == QuickStudy.allStudyCardsList.size() - 1) {
                        previewIndex = 0;
                    } else {
                        previewIndex++;
                    }
                } else {

                    rotationTimer = 2F;
                    if (QuickStudy.allStudyCardsListUpgraded.size() == 0) {
                        cardsToPreview = CardLibrary.cards.get("Madness");
                    } else {
                        cardsToPreview = QuickStudy.allStudyCardsListUpgraded.get(previewIndex);
                    }
                    if (previewIndex == QuickStudy.allStudyCardsListUpgraded.size() - 1) {
                        previewIndex = 0;
                    } else {
                        previewIndex++;
                    }
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void unhover() {
        super.unhover();
        cardsToPreview = null;
    }
}

